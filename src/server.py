import os
import sys

# Add the current directory to path so it can find jvm_utils.py
sys.path.append(os.path.dirname(__file__))

# USE THE OFFICIAL IMPORT PATH
from mcp.server.fastmcp import FastMCP
from jvm_utils import JVMUtils

# Create the MCP server object
mcp = FastMCP("JVM-Diagnostic-Tool")
jvm = JVMUtils()

@mcp.tool()
def list_java_apps() -> str:
    """List all running Java applications and their PIDs."""
    return jvm.list_processes()

@mcp.tool()
def analyze_threads(pid: str) -> str:
    """Get a full thread dump for a PID to diagnose hangs or high CPU."""
    return jvm.get_thread_dump(pid)

@mcp.tool()
def check_memory_health(pid: str) -> str:
    """Check the heap memory usage for a specific Java process."""
    return jvm.get_heap_summary(pid)

if __name__ == "__main__":
    mcp.run()