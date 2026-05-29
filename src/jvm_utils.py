import subprocess

class JVMUtils:
    @staticmethod
    def run_command(command: list) -> str:
        try:
            result = subprocess.run(command, capture_output=True, text=True, check=True)
            return result.stdout
        except subprocess.CalledProcessError as e:
            return f"Error: {e.stderr}"
        except FileNotFoundError:
            return "Error: JDK tools not found. Ensure JAVA_HOME is set and in PATH."

    def list_processes(self):
        # jps -l shows the Process ID and the full package name
        return self.run_command(["jps", "-l"])

    def get_thread_dump(self, pid: str):
        # jstack provides the current state of all threads
        return self.run_command(["jstack", pid])

    def get_heap_summary(self, pid: str):
        # jcmd provides a quick heap summary
        return self.run_command(["jcmd", pid, "GC.heap_info"])